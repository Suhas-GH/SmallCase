const form = document.querySelector("form");
fField = form.querySelector(".firstName");
fInput = fField.querySelector("input");
lField = form.querySelector(".lastName");
lInput = lField.querySelector("input");

uField = form.querySelector(".userName");
uInput = uField.querySelector("input");
pField = form.querySelector(".password");
pInput = pField.querySelector("input");

const lowerCaseLetters = /[a-z]/g;
const upperCaseLetters = /[A-Z]/g;
const numbers = /[0-9]/g;
const specialCharacters = /[!@#$%^&*]/g;

form.onsubmit = (e) => {
    e.preventDefault(); //preventing from form submitting
    //if username and password is blank then add shake class in it else call specified function
    (uInput.value === "") ? uField.classList.add("shake", "error") : checkUName();
    (pInput.value === "") ? pField.classList.add("shake", "error") : checkPass();

    //if firstName and lastName is blank then add shake class in it else call specified function
    (fInput.value === "") ? fField.classList.add("shake", "error") : checkFName();
    (lInput.value === "") ? lField.classList.add("shake", "error") : checkLName();

    setTimeout(()=>{ //remove shake class after 500ms
        uField.classList.remove("shake");
        pField.classList.remove("shake");
        fField.classList.remove("shake");
        lField.classList.remove("shake");
    }, 500);

    uInput.onkeyup = () => { checkUName();} //calling checkUName function on email input keyup
    pInput.onkeyup = () => { checkPass(); } //calling checkPassword function on pass input keyup
    fInput.onkeyup = () => { checkFName(); } //calling checkFName function on email input keyup
    lInput.onkeyup = () => { checkLName(); } //calling checkFName function on pass input keyup

    function checkUName(){ //checkUName function
        if (uInput.value === "") { //if uname is empty then add error and remove valid class
            uField.classList.add("error");
            uField.classList.remove("valid");
        }
        else { //if uname is empty then remove error and add valid class
            uField.classList.remove("error");
            uField.classList.add("valid");
        }
    }

    function checkPass(){ //checkPass function
        if(pInput.value === "" || !pInput.value.match(lowerCaseLetters) || !pInput.value.match(upperCaseLetters)
            || !pInput.value.match(numbers) || !pInput.value.match(specialCharacters) || pInput.value.length < 8){ //if pass is empty then add error and remove valid class
            pField.classList.add("error");
            pField.classList.remove("valid");
        }else{ //if pass is empty then remove error and add valid class
            pField.classList.remove("error");
            pField.classList.add("valid");
        }
    }

    function checkFName() { //checkFName function
        if (fInput.value === "") { //if fname is empty then add error and remove valid class
            fField.classList.add("error");
            fField.classList.remove("valid");
        } else { //if fname is empty then remove error and add valid class
            fField.classList.remove("error");
            fField.classList.add("valid");
        }
    }

    function checkLName() { //checkLName function
        if (lInput.value === "") { //if lname is empty then add error and remove valid class
            lField.classList.add("error");
            lField.classList.remove("valid");
        } else { //if lname is empty then remove error and add valid class
            lField.classList.remove("error");
            lField.classList.add("valid");
        }
    }

    if(uInput.value !== "" && pInput.value !== "" && fInput.value !== "" && lInput.value !== ""
        && pInput.value.match(lowerCaseLetters) && pInput.value.match(upperCaseLetters)
        && pInput.value.match(numbers) && pInput.value.match(specialCharacters) && pInput.value.length >= 8){
        form.submit();
    }
}
