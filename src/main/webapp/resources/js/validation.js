function validateIndexForm(formName) {
    var email = document.forms[formName]["email"].value;
    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
        alert("Email not valid");
        return false;
    }
    var password = document.forms[formName]["password"].value;
    if (password == null || password == "" || password.length < 5) {
        alert("Password must be at least 5 chars");
        return false;
    }
}

function validateLoginForm() {
    return validateIndexForm("loginForm");
}

function validateRegForm() {
    var name = document.forms["regForm"]["username"].value;
    if (name == null || name == "" || name.length < 3) {
        alert("Name must be at least 3 chars");
        return false;
    }
    return validateIndexForm("regForm");
}

// service page

var clicked = false;
function addInput(divName){
    if (!clicked) {
        var newDiv = document.createElement('div');
        newDiv.innerHTML = "Name   <br><input type='text' name='name'><br>" +
            "Age    <br><input type='text' name='age'><br>" +
            "Rating <br><input type='text' name='rating'><br>" +
            "Submit <br><input type='submit' name='service_button' value='add' class='command_btns btn'><br>";
        document.getElementById(divName).appendChild(newDiv);

    } else {
        document.getElementById(divName).innerHTML = ""
    }
    clicked = !clicked;
}

function validateForm(formName) {
    var name = document.forms[formName]["name"].value;
    if (name == null || name == "" || name.length < 3) {
        alert("Name must be at least 3 chars");
        return false;
    }
    var age = document.forms[formName]["age"].value;
    if (age == null || age == "" || age <= 0 || age > 120) {
        alert("Age must be integer in range 1 - 120");
        return false;
    }
    var rating = document.forms[formName]["rating"].value;
    if (rating == null || rating == "" || isNaN(rating) || rating < 0 || rating > 5) {
        alert("Rating must be float in range 0.0 - 5.0");
        return false;
    }
}

function validateAddForm() {
    return validateForm("addForm");
}

function validateUpdateForm() {
    return validateForm("updateForm");
}

var visible = false;
function makeDeleteButtonVisible() {
    var deleteButtons = document.getElementsByClassName("deleteButton");
    for (var i = 0, len = deleteButtons.length; i < len; i++) {
        if (visible) {
            deleteButtons[i].style.display="none";
        } else {
            deleteButtons[i].style.display="block";
        }
    }
    visible = !visible;
}

var update = true;
function showHideUpdate() {
    var deleteButtons = document.getElementsByClassName("infoLabel");
    var updateInputs = document.getElementsByClassName("updateInput");
    for (var i = 0, len = deleteButtons.length; i < len; i++) {
        if (update) {
            deleteButtons[i].style.display = "none";
            updateInputs[i].style.display = "block";
        } else {
            deleteButtons[i].style.display = "block";
            updateInputs[i].style.display = "none";
        }
    }
    update = !update;
}

