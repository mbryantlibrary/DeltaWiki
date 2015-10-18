from flask import Flask, request
from flask import jsonify

from flask_restful import Resource, Api


app = Flask(__name__)
api = Api(app)

pages = {}

class PagesResource(Resource):

    def get(self,pagename):
        try:
            return {'pageName':pagename, 'pageContent':pages[pagename]}
        except KeyError:
            return {'pageNotFound':True}, 404

    def put(self,pagename):

        pages[pagename]=request.json['pageContent'];
        return {'pageName':pagename, 'pageContent':pages[pagename]}



api.add_resource(PagesResource,"/api/page/<string:pagename>")


app.run(debug=True)
