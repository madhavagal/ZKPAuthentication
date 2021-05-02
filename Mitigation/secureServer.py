import sys
import traceback
import os
from flask import *
from flask import request
import requests
import time
from datetime import datetime

threshold = 100
fmt = '%Y-%m-%d %H:%M:%S'
app = Flask(__name__)
@app.route("/api/auth",methods=["POST"])
def getdata():
	data = request.get_json()["data"]
	li = data.split(',')
	if (len(li) != 10):
		return Response("Authentication failed.. invalid number of arguments",status = 400, mimetype='application/text')

	a = li[0]
	b = li[1]
	c = li[2]
	d = li[3]
	e = li[4]
	f = li[5]
	g = li[6]
	h = li[7]
	i = li[8]
	timestamp = float(li[9])
	timeAsString = li[9]
	global threshold
	global fmt
	time = datetime.fromtimestamp(timestamp).strftime(fmt)
	now = datetime.now().strftime(fmt)
	print("\ntimestamp received -> "+time)
	print("current time -> "+now)
	delta = datetime.strptime(now, fmt) - datetime.strptime(time, fmt)
	deltaSeconds = delta.total_seconds()
	print("diff in seconds = "+str(deltaSeconds))
	proverIP = request.environ.get('HTTP_X_REAL_IP', request.remote_addr)
	print("prover's IP -> "+proverIP)
	if ( deltaSeconds >= threshold or deltaSeconds < 0):
		print("packet expired..")
		return Response("Authentication failed.. packet expired",status = 400, mimetype='application/text')
	
	os.system("sudo python /home/madhav/Desktop/CapstoneProject/Mitigation/secureAttempt.py %s %s %s %s %s %s %s %s %s %s %s" % (a,b,c,d,e,f,g,h,i,timeAsString,proverIP))
	f = open("/home/madhav/Desktop/CapstoneProject/Mitigation/out.txt", "r")
	output = f.read().strip()
	#print("output is "+output)
	val = output == "y"
	#print("val is "+str(val))
	if val:
		return Response("Authenticated successfully",status = 200, mimetype='application/text')
	return Response("Authentication failed",status = 400, mimetype='application/text')

if(__name__ == "__main__"):
	app.run(host="0.0.0.0",port="3999",debug=True)
