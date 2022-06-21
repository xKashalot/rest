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
            tBody += ('<td>' + object.userId + '</td>');
            tBody += ('<td>' + object.username + '</td>');
            tBody += ('<td>' + object.lastname + '</td>');
            tBody += ('<td>' + object.age + '</td>');
            tBody += ('<td>' + object.email + '</td>');
            tBody += ('<td>' + roles.replaceAll('ROLE_', ' ') + '</td>');
            tBody += ('<td> <button type="button" onclick="editModal(' + object.userId + ')" ' +
                'class="btn btn-primary">Edit</button></td>');
            tBody += ('<td><button type="submit" onclick="deleteModal(' + object.userId + ')" ' +
                'class="btn btn-danger">Delete</button></td>');
            tBody += ('</tr>');
        });
        $('#usersTable').html(tBody);
    }
}

refreshData();

// Create new user
function createUser(user) {
    fetch(requestUrl)
        .then() //

}


// Edit modal
function editModal(id) {
    fetch(requestUrl + '/' + id)
        .then(response => response.json())
        .then(result => userFields(result))

    function userFields(user) {
        $('#editID').val(user.userId);
        $('#editName').val(user.username);
        $('#editLastname').val(user.lastname);
        $('#editAge').val(user.age);
        $('#editEmail').val(user.email);
        $('#edit').attr('onclick', 'editUser(' + user.userId + ')')
        $('#editModal').modal()
    }
}

function editUser(id) {
    fetch(requestUrl + '/' + id,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'PUT',
            body: JSON.stringify(
                {
                    userId: document.getElementById('editID').value,
                    username: document.getElementById('editName').value,
                    lastname: document.getElementById('editLastname').value,
                    age: document.getElementById('editAge').value,
                    email: document.getElementById('editEmail').value,
                    roles: [document.getElementById('editRoles').value]
                }
            )
        }).then(() => {
        $('#editModal').modal('hide')
        refreshData()
    })
}

// Delete modal
function deleteModal(id) {
    fetch(requestUrl + '/' + id)
        .then(response => response.json())
        .then(result => userFields(result))

    function userFields(user) {
        $('#delID').val(user.userId);
        $('#delName').val(user.username);
        $('#delLastname').val(user.lastname);
        $('#delAge').val(user.age);
        $('#delEmail').val(user.email);
        $('#delete').attr('onclick', 'deleteUser(' + user.userId + ')')
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