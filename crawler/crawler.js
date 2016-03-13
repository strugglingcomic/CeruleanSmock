var recipe = {
	metdata: 
	{
		title: "Title",
		subtitle: "Subtitle",
		overview: "Overview",
		servings: 1,
		calories: 5000,
		cookTime: 60, // in minutes
	},

	ingredients: 
	[
		{
			name: "Ingredient1 Name",
			number: 5,
			measurement: "cup"
		},
		{
			name: "Ingredient2 Name",
			number: 15,
			measurement: "tablespoon"
		}
	],

	steps: 
	[
		{
			order: 1,
			title: "Title of Step",
			text: "Text of Step"

		},
		{
			order: 2,
			title: "Title of Step2",
			text: "Text of Step2"
		}
	]
}

function createCORSRequest(method, url){
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr){
        // XHR has 'withCredentials' property only if it supports CORS
        xhr.open(method, url, true);
    } else if (typeof XDomainRequest != "undefined"){ // if IE use XDR
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
        xhr = null;
    }
    return xhr;
}

$(document).ready(function(){
	$( "#result" ).load( "https://www.blueapron.com/recipes/cod-kedgeree-with-basmati-rice-eggs-frizzled-onion .ingredients-list", function() {
		alert("Loaded!");
	} );

})