from textblob import TextBlob
import cherrypy
import logging

class Sentiment(object):
    def analyze(self, string):
        analysis = TextBlob(string)
        return {'polarity' : analysis.sentiment.polarity}
    @cherrypy.expose()
    @cherrypy.tools.json_in()
    @cherrypy.tools.json_out()
    @cherrypy.tools.allow(methods=['POST'])
    def index(self):
        data = cherrypy.request.json
        print(data)
        if(len(data) == 0 or 'text' not in data):
            raise cherrypy.HTTPError(401, 'Invalid Request Parameters')
        return self.analyze(data['text'])
    
    
if __name__ == "__main__":
    cherrypy.quickstart(Sentiment(), '/')