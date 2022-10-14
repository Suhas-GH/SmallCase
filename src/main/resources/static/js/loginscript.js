const form = document.querySelector("form");
uField = form.querySelector(".userName"),
uInput = uField.querySelector("input"),
pField = form.querySelector(".password"),
pInput = pField.querySelector("input");

form.onsubmit = (e) => {
    e.preventDefault(); //preventing from form submitting
    //if username and password is blank then add shake class in it else call specified function
    (uInput.value === "") ? uField.classList.add("shake", "error") : checkUName();
    (pInput.value === "") ? pField.classList.add("shake", "error") : checkPass();

    setTimeout(()=>{ //remove shake class after 500ms
        uField.classList.remove("shake");
        pField.classList.remove("shake");
    }, 500);

    uInput.onkeyup = () => { checkUName();} //calling checkUName function on email input keyup
    pInput.onkeyup = () => { checkPass(); } //calling checkPassword function on pass input keyup

    function checkUName(){ //checkUName function
        if (uInput.value === "") { //if uname is empty then add error and remove valid class
            uField.classList.add("error");
            uField.classList.remove("valid");
        } else { //if uname is empty then remove error and add valid class
            uField.classList.remove("error");
            uField.classList.add("valid");
        }
    }

    function checkPass(){ //checkPass function
        if(pInput.value === ""){ //if pass is empty then add error and remove valid class
            pField.classList.add("error");
            pField.classList.remove("valid");
        }else{ //if pass is empty then remove error and add valid class
            pField.classList.remove("error");
            pField.classList.add("valid");
        }
    }

    if(uInput.value !== "" && pInput.value !== ""){
        form.submit();
    }
}
