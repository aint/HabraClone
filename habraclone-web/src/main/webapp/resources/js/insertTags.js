function edButton(id, display, tagStart, tagEnd, access, open) {
	this.id = id;					// used to name the toolbar button
	this.display = display;			// label on button
	this.tagStart = tagStart;		// open tag
	this.tagEnd = tagEnd;			// close tag
	this.access = access;			// set to -1 if tag does not need to be closed
	this.open = open;				// set to -1 if tag does not need to be closed
}

function edAddTag(button) {
	if (edButtons[button].tagEnd != '') {
		edOpenTags[edOpenTags.length] = button;
		document.getElementById(edButtons[button].id).value = '/'
				+ document.getElementById(edButtons[button].id).value;
	}
}

function edRemoveTag(button) {
	for (var i = 0; i < edOpenTags.length; i++) {
		if (edOpenTags[i] == button) {
			edOpenTags.splice(i, 1);
			document.getElementById(edButtons[button].id).value = document.getElementById(edButtons[button].id)
					.value.replace('/', '');
		}
	}
}

function edCheckOpenTags(button) {
	var tag = 0;
	for (var i = 0; i < edOpenTags.length; i++) {
		if (edOpenTags[i] == button) {
			tag++;
		}
	}
	return tag > 0;
}

// insertion code
function edInsertTag(myField, i) {
	//IE support
	var sel;
	if (document.selection) {
		myField.focus();
		sel = document.selection.createRange();
		if (sel.text.length > 0) {
			sel.text = edButtons[i].tagStart + sel.text + edButtons[i].tagEnd;
		} else {
			if (!edCheckOpenTags(i) || edButtons[i].tagEnd == '') {
				sel.text = edButtons[i].tagStart;
				edAddTag(i);
			} else {
				sel.text = edButtons[i].tagEnd;
				edRemoveTag(i);
			}
		}
		myField.focus();
	}
	//MOZILLA/NETSCAPE support
	else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		var cursorPos = endPos;
		var scrollTop = myField.scrollTop;
		if (startPos != endPos) {
			myField.value = myField.value.substring(0, startPos)
				+ edButtons[i].tagStart
				+ myField.value.substring(startPos, endPos)
				+ edButtons[i].tagEnd
				+ myField.value.substring(endPos, myField.value.length);
			cursorPos += edButtons[i].tagStart.length + edButtons[i].tagEnd.length;
		} else {
			if (!edCheckOpenTags(i) || edButtons[i].tagEnd == '') {
				myField.value = myField.value.substring(0, startPos)
					+ edButtons[i].tagStart
					+ myField.value.substring(endPos, myField.value.length);
				edAddTag(i);
				cursorPos = startPos + edButtons[i].tagStart.length;
			} else {
				myField.value = myField.value.substring(0, startPos)
					+ edButtons[i].tagEnd
					+ myField.value.substring(endPos, myField.value.length);
				edRemoveTag(i);
				cursorPos = startPos + edButtons[i].tagEnd.length;
			}
		}
		myField.focus();
		myField.selectionStart = cursorPos;
		myField.selectionEnd = cursorPos;
		myField.scrollTop = scrollTop;
	} else {
		if (!edCheckOpenTags(i) || edButtons[i].tagEnd == '') {
			myField.value += edButtons[i].tagStart;
			edAddTag(i);
		} else {
			myField.value += edButtons[i].tagEnd;
			edRemoveTag(i);
		}
		myField.focus();
	}
}

function edInsertLink(myField, i, defaultValue) {
	if (!defaultValue) {
		defaultValue = 'http://';
	}
	if (!edCheckOpenTags(i)) {
		var URL = prompt('Enter the URL', defaultValue);
		if (URL) {
			edButtons[i].tagStart = '<a href="' + URL + '" target="_blank">';
			edInsertTag(myField, i);
		}
	} else {
		edInsertTag(myField, i);
	}
}

/**************************************/

var edButtons = [];
var edOpenTags = [];

edButtons[edButtons.length] = new edButton('tag_bold', 'b', '<b>', '</b>', 'b');
edButtons[edButtons.length] = new edButton('tag_italic', 'i', '<i>', '</i>', 'i');
edButtons[edButtons.length] = new edButton('tag_del', 'del', '<del>', '</del>', 'd');
edButtons[edButtons.length] = new edButton('tag_link', 'link', '', '</a>', 'a');
edButtons[edButtons.length] = new edButton('tag_block', 'code', '@pre-code@', '@code-pre@', 'q');
var extendedStart = edButtons.length;