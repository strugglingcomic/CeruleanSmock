#
##
###Saves all the recipe sites' HTML for quicker processing
##
#

from bs4 import BeautifulSoup
import urllib
import json
#Converts unicode fractions like '\xbc' to float
import unicodedata

tempArray = ["https://www.blueapron.com/recipes/cod-kedgeree-with-basmati-rice-eggs-frizzled-onion", "https://www.blueapron.com/recipes/chicken-cacciatore-with-fettuccine-pasta-mushrooms"]

#Takes an array of website links
def downloadHTML(websiteArray):
	#siteHTMLArray = []
	for site in websiteArray:
		print (site)
		siteHTML = BeautifulSoup(urllib.urlopen(site).read())
		#siteHTMLArray.append(siteHTML)
		with open('rawhtmlFromSites.txt', 'a') as outfile:
			#outfile.write(str(siteHTMLArray))
			outfile.write(str(siteHTML))
			outfile.write('\n')
			# Allows for easier parsing of text file in crawler.py
			outfile.write('<SPLITHTMLHERE>')
			outfile.write('\n')


downloadHTML(tempArray)