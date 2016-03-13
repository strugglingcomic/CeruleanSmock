# Parses blue_apron_raw data to give array of Blue Apron Links

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
	with open('blueApronLinks.json', 'w') as outfile:
		json.dump(recipeArray, outfile)

findAllLinks();
print recipeArray

