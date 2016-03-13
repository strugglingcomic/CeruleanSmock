recipeArray = []

from bs4 import BeautifulSoup
import urllib
import json

r = urllib.urlopen('blue_apron_raw.html').read()
soup = BeautifulSoup(r)

def findAllLinks():
	allLinks = soup.findAll("a")
	print len(allLinks)
	for link in allLinks: 
		newLink = "https://www.blueapron.com" + link['href']
		print newLink
		recipeArray.append(newLink)

findAllLinks();
print recipeArray

