function showChangePasswordForm() {
  document.getElementById("changePasswordForm").classList.toggle("hidden");
}

function checkPassword() {
   if (document.getElementById('password').value ==
     document.getElementById('confirm_password').value) {
     document.getElementById('confirm_password').style.borderColor = "green"
     document.getElementById('password').style.borderColor = "black"
     return true
   } else {
     document.getElementById('confirm_password').style.borderColor = "black"
     return false
   }
 }

 function checkErrorPassword(){
    if (!checkPassword()) {
        document.getElementById('errorPassword').innerHTML = "passwords are different";
        document.getElementById('password').style.borderColor = "red"
    }
 }