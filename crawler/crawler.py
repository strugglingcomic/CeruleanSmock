from bs4 import BeautifulSoup
import urllib
import json
#Allows for line separation 
import os

# Grab the URL for Blue Apron
#r = urllib.urlopen('https://www.blueapron.com/recipes/cod-kedgeree-with-basmati-rice-eggs-frizzled-onion').read()
#soup = BeautifulSoup(r)

global soup

#create json data structure
data = {
	"metadata": 
	{
	},
	"ingredients": [],

	"steps": 
	[]
}


json_data = json.dumps(data)

# Prase website for metadata
def findmetadata():
	tempMetaData = {
		"title": "",
		"subtitle": "",
		"overview": "",
		"servings": 0,
		"calories": 0,
		"cookTime": 0
	}
	# Find main title 
	tempMetaData["title"] = soup.find("h1", { "class" : "main-title" }).text.replace("\n", "")
	# Find subtitle
	tempMetaData["subtitle"] = soup.find("h2", { "class" : "sub-title"}).text.replace("\n", "")
	# Find overview/description
	tempMetaData["overview"] = soup.find("p", { "itemprop" : "description"}).text
	
	#Find servings
	tempServings = soup.find("span", { "itemprop" : "recipeYield"}).text
	# Parses the text, pulls out integers, outputs array
	tempServings = [int(s) for s in tempServings.split() if s.isdigit()]
	tempMetaData["servings"] = tempServings[0]

	# Blue Apron stores integer for calories in its own span
	tempCalories = int(soup.find("span", { "itemprop" : "calories"}).text)
	tempMetaData["calories"] = tempCalories

	# Cook Time is stored under the third paragraph of the rec-description-area
	tempCookTime = soup.find("div", { "class" : "rec-description-area"})
	tempCookTime = tempCookTime.findAll("p")[2].text
	# Parse Cook Time for just the integer and spit out array
	tempCookTime = [int(s) for s in tempCookTime.split() if s.isdigit()]
	# Set cook time to the maximum cooktime
	tempMetaData["cookTime"] = max(tempCookTime)
	#Append struct to data
	data["metadata"] = tempMetaData


# Find ingredient name
def findIngredientData ():
	dataIngredients = soup.findAll("li", itemprop="ingredients")
	for i in range(0, len(dataIngredients)):
		# If it's a linked ingredient
		if dataIngredients[i].find('a'): 
			# defin
			tempIngStruct = {
				"name" : "",
				"number" : 0,
				"dimension": ""
			}
			# Add name of ingredient
			ingredientName = dataIngredients[i]('a')[0]['data-ingredient']
			tempIngStruct["name"] = ingredientName
			ingredientAmount = dataIngredients[i].find("span", {"class" : "amount"}).text
			tempIngStruct["number"] = ingredientAmount
			#To get the dimension/measurement (i.e. cup), grab the text of the link
			# Then replace the ingredientName and ingredientAmount with nothing to isolate dimension only
			ingredientDim = dataIngredients[i].find('a').text
			ingredientDim = ingredientDim.replace(ingredientName, "").replace(ingredientAmount, "").replace("\n", "")
			tempIngStruct["dimension"] = ingredientDim.replace(" ", "")
			data['ingredients'].append(tempIngStruct)
		# If it's a non-linked ingredient
		else:
			tempIngStruct = {
				"name" : "",
				"number" : 0,
				"dimension": ""
			}
			tempIngStruct["name"] = dataIngredients[i]('div')[0].contents[2].replace("\n", "")
			tempIngStruct["number"] = dataIngredients[i].find("span", {"class" : "amount"}).text
			data['ingredients'].append(tempIngStruct)

def findSteps (): 
	ingredientsDiv = soup.findAll("div", itemprop="recipeInstructions")
	# On Blue Apron, the instructions are repeated twice. Cut the second half out of list
	ingredientsDiv = ingredientsDiv[:len(ingredientsDiv)/2]
	for step in ingredientsDiv:
		tempSteps = {
		"order" : 0,
		"title" : "Step Title",
		"text" : "Text of step"
		}
		# Set the order to the Step # from site
		tempSteps["order"] = int(step.find("span", {"class" : "instr-num"}).text)
		# Grab title, replace all new line entries
		tempSteps["title"] = step.find("span", {"class" : "instr-title"}).text.replace("\n", "")
		# Grab the instruction text
		tempSteps["text"] = step.find("div", {"class" : "instr-txt"}).text.replace("\n", "")
		#Append this step to the overall data structure
		data["steps"].append(tempSteps)
'''
def recipetoJSON(recipeURL): 
	# read the URL where receipe sits
	recipeSite = urllib.urlopen(recipeURL).read()
	global soup
	soup = BeautifulSoup(recipeSite)

	findmetadata();
	findIngredientData();
	findSteps();
	# Export to data.json text file
	import json
	with open('data.json', 'w') as outfile:
	    json.dump(data, outfile)
'''

def recipetoJSON(recipeURLArray): 
	# read the URL where receipe sits
	print len(recipeURLArray)
	for recipe in recipeURLArray: 
		recipeSite = urllib.urlopen(recipe).read()
		global soup
		soup = BeautifulSoup(recipeSite)

		findmetadata();
		findIngredientData();
		findSteps();
		# Export to data.json text file
		with open('data.json', 'a') as f:
			json.dump(data, f, indent=1)


recipetoJSON([
	'https://www.blueapron.com/recipes/lentil-fennel-minestra-with-asparagus-tempura-lemon-aioli',
	'https://www.blueapron.com/recipes/english-pea-goat-cheese-quiches-with-pea-shoot-shaved-parmesan-salad'
	])

