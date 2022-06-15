let requestUrl = 'http://localhost:8080/admin/users'

// Users table
// refreshData() -обновить таблицу без перезагрузки
function refreshData() {
    fetch(requestUrl)
        .then(response => response.json())
        .then(result => refreshTable(result))

        function refreshTable(){
            //chtoto bydet
        }


}


// Create new user
function createUser(user){
    fetch(requestUrl)
        .then() //

}


// Edit modal
function editModal(id) {
    fetch(requestUrl + id)
        .then(response => response.json())
        .then(result => userFields(result))
    function userFields(user) {
        $('#editID').val(user.id);
        $('#editName').val(user.username);
        $('#editLastname').val(user.lastname);
        $('#editAge').val(user.age);
        $('#editEmail').val(user.email);
        $('#editPassword').val(user.password);
        $('#editPasswordConfirm').val(user.passwordConfirm);
        
    }
}
function editUser(id){

    refreshData()
}



// Delete modal
function deleteModal(id) {

    function deleteUser(id) {

    }

    refreshData()
}



