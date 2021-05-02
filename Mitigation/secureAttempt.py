import jpype
import sys
import traceback
import os

def main(a,b,c,d,e,f,g,h,i,j,k):
	classpath = "-Djava.class.path=%s" % "/home/madhav/Desktop/CapstoneProject/Mitigation/"
	jpype.startJVM("/usr/lib/jvm/java-11-openjdk-amd64/lib/server/libjvm.so", classpath)
	Main = jpype.JPackage("secure").Main
	Main.main([a,b,c,d,e,f,g,h,i,j,k])
            
if __name__ == "__main__":
	# expect 11 arguments
	a = sys.argv[1]
	b = sys.argv[2]
	c = sys.argv[3]
	d = sys.argv[4]
	e = sys.argv[5]
	f = sys.argv[6]
	g = sys.argv[7]
	h = sys.argv[8]
	i = sys.argv[9]
	j = sys.argv[10]
	k = sys.argv[11]
	main(a, b, c, d, e, f, g, h, i, j, k)
