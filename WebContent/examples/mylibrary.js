// Define the namespace
var mylibrary = mylibrary || {};

mylibrary.MyComponent = function (element) {
	this.element = element;
	this.element.innerHTML =
		"<div class='caption'>Hello, world!</div>" +
		"<div class='textinput'>Enter a value: " +
		"<input type='text' name='value'/>" +
		"<input type='button' value='Click'/>" +
		"</div>";

	// Style it
	this.element.style.border = "thin solid red";
	this.element.style.display = "inline-block";

	// Getter and setter for the value property
	this.getValue = function () {
		return this.element.
		    getElementsByTagName("input")[0].value;
	};
	this.setValue = function (value) {
		this.element.getElementsByTagName("input")[0].value =
		    value;
	};

	// Default implementation of the click handler
	this.click = function () {
		alert("Error: Must implement click() method");
	};

	// Set up button click
	var button = this.element.getElementsByTagName("input")[1];
	var component = this; // Can't use this inside the function
	button.onclick = function () {
		component.click();
	};
};
