let requestUrl = 'http://localhost:8080/admin/users'

// Users table
function refreshData() {
    fetch(requestUrl)
        .then(response => response.json())
        .then(result => refreshTable(result))

    function refreshTable(users) {
        let tBody = '';
        $('#usersTable').find('tr').remove();
        $.each(users, function (key, object) {
            let roles = ''
            $.each(object.roles, function (k, o) {
                roles += o.role
            })
            tBody += ('<tr>');
            tBody += ('<td>' + object.id + '</td>');
            tBody += ('<td>' + object.username + '</td>');
            tBody += ('<td>' + object.lastname + '</td>');
            tBody += ('<td>' + object.age + '</td>');
            tBody += ('<td>' + object.email + '</td>');
            tBody += ('<td>' + roles.replaceAll('ROLE_', ' ') + '</td>');
            tBody += ('<td> <button type="button" onclick="editModal(' + object.id + ')" ' +
                'class="btn btn-primary">Edit</button></td>');
            tBody += ('<td><button type="submit" onclick="deleteModal(' + object.id + ')" ' +
                'class="btn btn-danger">Delete</button></td>');
            tBody += ('</tr>');
        });
        $('#usersTable').html(tBody);
    }
}

refreshData();

// // Create new user
// function createUser(user) {
//     fetch(requestUrl)
//         .then() //
//
// }


// // Edit modal
// function editModal(id) {
//     fetch(requestUrl + id)
//         .then(response => response.json())
//         .then(result => userFields(result))
//
//     function userFields(user) {
//         $('#editID').val(user.id);
//         $('#editName').val(user.username);
//         $('#editLastname').val(user.lastname);
//         $('#editAge').val(user.age);
//         $('#editEmail').val(user.email);
//         $('#editPassword').val(user.password);
//         $('#editPasswordConfirm').val(user.passwordConfirm);
//     }
//
//     //refreshData()
// }
//
// function editUser(id) {
//
//     refreshData()
// }


// Delete modal
function deleteModal(id) {
    fetch(requestUrl + '/' + id)
        .then(response => response.json())
        .then(result => userFields(result))

    function userFields(user) {
        $('#delID').val(user.id);
        $('#delName').val(user.username);
        $('#delLastname').val(user.lastname);
        $('#delAge').val(user.age);
        $('#delEmail').val(user.email);
        $('#delete').attr('onclick', 'deleteUser(' + user.id + ')')
        $('#deleteModal').modal()
    }
}

function deleteUser(id) {
    fetch(requestUrl + '/' + id, {
        method: 'DELETE'
    }).then(() => {
        $('#deleteModal').modal('hide')
        refreshData();
    })
}