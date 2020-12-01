$(document).ready(function () {
    $('#upd').submit(function(event){
        event.preventDefault();
    });
    $('#newUser').submit(function(event){
        event.preventDefault();
    });
   /* $('.openPopup').on('click',function(){
        var dataURL = $(this).attr('data-href');
        $('.modal-body').load("/api/restful/users/{id}",function(){
            $('#edit').modal({show:true});
        });
    });*/
    editButtonEventListener();

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
        getUserById(id);
    });
}

function getUserById(id) {
    $.ajax({
        type: 'GET',
        url: '/api/restful/users/'+Number(id),
        async:false,
        success: function (data) {
            console.log('success', data);
                $('#idUserUpdate').val(data['id']),
                    $('#userNameUpdate').val(data['username']),
                    $('#nameUpdate').val(data['name']),
                    $('#lastnameUpdate').val(data['lastname'])/*,
                    $('#passwordUpdate').val(data['password']),
                    $('#userRoleUpdate').val(data['roles']),
                    $('#isActiveUpdate').val(data['isActive'])*/;
        }
    })
}



function updateUser() {
    let request = serializeUserForm();
    $.ajax({
        type: 'POST',
        url: '/api/restful/users/update/',
        contentType: "application/json",
        data: request,
        success: function (response) {
            console.log("user successfully updated " + request);
            RefreshTable();
        },
        error: function (data) {
            console.log("user update is failed " + request);
        }

    })
}

function serializeUserForm() {
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


/*function fillUsersTable(usersData){
    let tableContent ='';
    $.each(usersData,function (counter, data) {
        tableContent=tableContent+'<tr class="active">' +
        '                    <td> '+data['id']+' </td>' +
        '                    <td> '+data['name']+' </td>' +
        '                    <td> '+data['lastname']+' </td>' +
        '                    <td> '+data['username']+' </td>' +
        '                    <td> '+data['role']+' </td>' +
        '                    <td> '+data['isActive']+'</td>' +
        '                    <td> <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete">delete</button> </td>' +
        '                    <td> <button type="button" class="btn btn-success" data-toggle="modal" data-target="#edit">edit</button> </td>' +
        '                </tr>'
    }
        );
    console.log(tableContent);
    $('#allUsersTable').html(
    '      <thead>' +
    '                <tr class="active">' +
    '                    <th> id </th>' +
    '                    <th> name </th>' +
    '                    <th> lastname </th>' +
    '                    <th> username </th>' +
    '                    <th> role </th>' +
    '                    <th> is Active </th>' +
    '                    <th> Delete </th>' +
    '                    <th> Edit </th>' +
    '                </tr>' +
    '                </thead>' +
    '                <tbody>'+tableContent+'</tbody>'
        );
}*/

function addUser() {
    let request = serializeUserForm();
    $.ajax({
        type: 'POST',
        url: '/api/restful/users/add/',
        contentType: "application/json",
        data: request,
        success: function (response) {
            console.log("user successfully added " + request);
            RefreshTable();
        },
        error: function (data) {
            console.log("user update is failed " + request);
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
