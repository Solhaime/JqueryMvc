$(document).ready(function () {
    $('#upd').submit(function(event){
        event.preventDefault();
    });
    $('#newUser').submit(function(event){
        event.preventDefault();
    });
    $('#del').submit(function(event){
        event.preventDefault();
    });
    editButtonEventListener();
    deleteButtonEventListener();
    $("#successAlert").hide();
    $("#failAlert").hide();

});

function getAllUsers() {
    $.ajax({
        type: 'GET',
        url: '/api/restful/users/all',
        success: function (data) {
            console.log('success', data);
            $.each(data, function (i, item) {
            })
        }
    })
}

function editButtonEventListener(){
    $('.btn-success').click(function() {
        let id = $(this).parents('tr').find('.uLId').text();
        fillModalUpdate(id);
    });
}

function fillModalUpdate(id) {
    $.ajax({
        type: 'GET',
        url: '/api/restful/users/'+Number(id),
        async:false,
        success: function (data) {
            console.log('success', data);
                $('#idUserUpdate').val(data['id']),
                    $('#userNameUpdate').val(data['username']),
                    $('#nameUpdate').val(data['name']),
                    $('#lastnameUpdate').val(data['lastname']);
        }
    })
}
function deleteButtonEventListener(){
    $('.btn-danger').click(function() {
        let id = $(this).parents('tr').find('.uLId').text();
        fillModalDelete(id);
    });
}
function fillModalDelete(id) {
    $.ajax({
        type: 'GET',
        url: '/api/restful/users/'+Number(id),
        async:false,
        success: function (data) {
            console.log('success', data);
            $('#idUserDelete').val(data['id']),
                $('#userNameDelete').val(data['username']),
                $('#nameDelete').val(data['name']),
                $('#lastnameDelete').val(data['lastname']);
        }
    })
}



function updateUser() {
    let request = serializeUserFormUpd();
    $.ajax({
        type: 'POST',
        url: '/api/restful/users/update',
        contentType: "application/json",
        data: request,
        success: function (response) {
            console.log("user successfully updated " + request);
            RefreshTable();
            editButtonEventListener();
        },
        error: function (data) {
            console.log("failed to update user" + request);
        }

    })
}
function deleteUser() {
    let id =$('#idUserDelete').val();
    $.ajax({
        type: 'DELETE',
        url: '/api/restful/users/delete/'+Number(id),
        contentType: "application/json",
        success: function (response) {
            console.log("user successfully deleted " +id);
            RefreshTable();
            deleteButtonEventListener();
        },
        error: function (data) {
            console.log("failed to delete user" + id);
        }

    })
}

function serializeUserFormUpd() {
    return JSON.stringify( {
        'id':Number($('#idUserUpdate').val()),
        'name': $('#nameUpdate').val(),
        'lastname': $('#lastnameUpdate').val(),
        'username': $('#userNameUpdate').val(),
        'password': $('#passwordUpdate').val(),
        'roles': $('#userRoleUpdate').val(),
        'isActive': $('#isActiveUpdate').val()

    })

}

function serializeUserFormAdd() {
    return JSON.stringify( {
        'name': $('#nameCreate').val(),
        'lastname': $('#lastnameCreate').val(),
        'username': $('#usernameCreate').val(),
        'password': $('#passwordCreate').val(),
        'roles': $('#userRoleCreate').val()
    })

}

function addUser() {
    let request = serializeUserFormAdd();
    $.ajax({
        type: 'POST',
        url: '/api/restful/users/create',
        contentType: "application/json",
        data: request,
        success: function (response) {
            console.log("user successfully added " + request);
            RefreshTable();
           let w = $('#successAlert').show();
            setTimeout(function() {w.hide();}, 2000);
        },
        error: function (data) {
            console.log("failed to add user" + request);
            let z = $('#failAlert').show();
            setTimeout(function() {z.hide();}, 2000);
        }

    })
}
/*
function fillUsersTable() {
    setTimeout(function () {
        $("#allUsersTable").load("http://localhost:8080/test #allUsersTable");
    }, 2000);
}*/

function RefreshTable() {
    $("#allUsersTable").load("http://localhost:8080/test #allUsersTable");
    console.log("message");
}
