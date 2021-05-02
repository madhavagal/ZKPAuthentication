import sys
import traceback
import os
from flask import *
from flask import request
import requests
import time

app = Flask(__name__)
@app.route("/api/auth",methods=["POST"])
def getdata():
	data = request.get_json()["data"]
	li = data.split(',')
	a = li[0]
	b = li[1]
	c = li[2]
	d = li[3]
	e = li[4]
	f = li[5]
	g = li[6]
	h = li[7]
	i = li[8]
	os.system("sudo python /home/madhav/Desktop/CapstoneProject/Demonstration/vulnerableAttempt.py %s %s %s %s %s %s %s %s %s" % (a,b,c,d,e,f,g,h,i))
	f = open("/home/madhav/Desktop/CapstoneProject/Demonstration/out.txt", "r")
	output = f.read().strip()
	print("\noutput is "+output)
	val = output == "y"
	print("val is "+str(val))
	if val:
		return Response("Authenticated successfully",status = 200, mimetype='application/text')
	return Response("Authentication failed",status = 400, mimetype='application/text')

if(__name__ == "__main__"):
	app.run(host="0.0.0.0",port="3999",debug=True)
