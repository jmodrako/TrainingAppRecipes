#!flask/bin/python
# coding: latin-1
from flask import Flask, jsonify, abort, make_response, request
from flask.ext.httpauth import HTTPBasicAuth
auth = HTTPBasicAuth()

app = Flask(__name__)

recipes = [
	{
		'id': 1,
		'title': 'Karpatka',
		'subtitle': 'Created by Misiu on Monday, 16.10.2004',
		'description': 'Dobre ciasto! Palce lizać kremik pierwsza klasa! Połączenie kremu i tłuszczu które napewno dostarczy dużo kilogramów.',
		'imageUrl': '/static/karpatka.png'
	},
	{
		'id': 2,
		'title': 'Karkówka',
		'subtitle': 'Created by Misiu on Friday, 30.09.2013',
		'description': 'Świeżo bita, mięsko pierwszego sortu z przetartych biedronek i krów. Zjesz ja a dostaniesz biegunki = dużo czasu na skupienie',
		'imageUrl': '/static/karkowka.png'
	},
	{
		'id': 3,
		'title': 'Sałatka Gyros',
		'subtitle': 'Created by Misiu on Sunday, 16.01.2015',
		'description': 'Samo zdrowie! Oprócz tego ze każde warzywko a nawet sól ma w sobie chemię która Cię utuczy jak tego kurczaka w tej sałatce to jest smaczna - ale naprawde smaczna :). Żyć nie umierać - no chyba ze zjesz jej tyle ze pękniesz :P',
		'imageUrl': '/static/salatka.png'
	}
]

#@auth.get_password
#def get_password(username):
#	if username == 'oltex':
#		return 'python'
#	return None

@auth.error_handler
def unauthorized():
	return make_response(jsonify({'error': 'Unauthorized access'}), 403)

@app.route('/cookbook/api/v1.0/recipes', methods=['GET'])
#@auth.login_required
def get_recipes():
	return jsonify({'recipes': recipes})

@app.route('/cookbook/api/v1.0/recipes/<int:recipe_id>', methods=['GET'])
def get_recipe(recipe_id):
	recipe = [recipe for recipe in recipes if recipe['id'] == recipe_id]
	if len(recipe) == 0:
		abort(404)
	return jsonify(recipe[0])

@app.errorhandler(404)
def not_found(error):
	return make_response(jsonify({'error': 'Not found'}), 404)

@app.route('/cookbook/api/v1.0/recipes', methods=['POST'])
def create_recipe():
	if not request.json or not 'title' in request.json:
		abort(400)
	recipe = {
		'id': recipes[-1]['id'] + 1,
		'title': request.json['title'],
		'subtitle': request.json['subtitle'],
		'description': request.json['description'],
		'image_url': request.json['image_url']
		}
	recipes.append(recipe)
	return (jsonify({'recipe': recipe}), 201)

@app.route('/cookbook/api/v1.0/recipes/<int:recipe_id>', methods=['PUT'])
def update_recipe(recipe_id):
	recipe = [recipe for recipe in recipes if recipe['id'] == recipe_id]
	if len(recipe) == 0:
		abort(404)
	if not request.json:
		abort(400)
	if 'title' in request.json and type(request.json['title']) != unicode:
		abort(400)
	if 'description' in request.json and type(request.json['description']) is not unicode:
		abort(400)
	recipe[0]['title'] = request.json.get('title', recipe[0]['title'])
	recipe[0]['subtitle'] = request.json.get('subtitle', recipe[0]['subtitle'])
	recipe[0]['description'] = request.json.get('description', recipe[0]['description'])
	recipe[0]['image_url'] = request.json.get('image_url', recipe[0]['image_url'])
	return jsonify({'recipe': recipe[0]})

@app.route('/cookbook/api/v1.0/recipes/<int:recipe_id>', methods=['DELETE'])
def delete_recipe(recipe_id):
	recipe = [recipe for recipe in recipes if recipe['id'] == recipe_id]
	if len(recipe) == 0:
		abort(404)
	recipes.remove(recipe[0])
	return jsonify({'result': True})

if __name__ == '__main__':
	app.debug = True
	app.run(host='0.0.0.0')
