/*global define */

'use strict';

define(function() {

/* Controllers */

var controllers = {};

//LOGOUT

controllers.Logout = function($scope, $http, $log, $window, $cookies) {
        $http.get('logout').then(function(data){
            $window.location.href = '#/';
        });
}
controllers.Logout.$inject = ['$scope', '$http', '$log', '$window', '$cookies'];
//LOGIN

controllers.Login = function($scope, $http, $log, $window) {
    $scope.login = function(){
        $http.post('login', $scope.vm).then(function(data, status, headers, config) {
        if($scope.vm.error){
            delete $scope.vm.error
        }
        $http.defaults.headers.common['X-AUTH-TOKEN'] = data.data.authToken ;
        $window.location.href = '#/view';
        },
        function(){
            $scope.vm.error = "Invalid Credentials! Please try again."
        }
        );
    }
    $scope.register = function(){
        $http.post('register', $scope.vm.user)
                               .then(function(data) {
            authProvider.setUser(data);
            $window.location.href = '#/';
        },
        function(){
            $scope.vm.error = "User Name Exists in System! Please choose another."
        }
        );
    }
    $scope.logout = function(){
        authProvider.setUser(null);
        $window.location.href = '#/';
    }
}

controllers.Login.$inject = ['$scope', '$http', '$log', '$window'];

//CREATE Controller

controllers.Create = function($scope, $http, $log, $window) {
    $scope.addTicket = function(){
        $http.post('app/ticket', $scope.data).then(function(data) {
                $window.location.href = '#/edit/'+data.data.id;
            },
            function(){
            //TODO Error handling
            }
            );
    };
    $scope.cancel = function(){
        $window.location.href = '#/view';
    }
};

controllers.Create.$inject = ['$scope', '$http', '$log', '$window'];

//VIEW Controller

controllers.View = function($scope, $http, $log, $window) {
    $scope.batchSize = 20
    $scope.qSize = 20;
    $scope.qPage = 0;
    $scope.canPage = false;

    $http.get('app/ticket?page='+$scope.qPage+'&size='+$scope.qSize).success(function(data){
        $scope.tickets = data;
        $scope.canPage = true;
        $scope.qPage = $scope.qPage + $scope.batchSize;
    });
    $scope.viewTicket = function(ticketId){
        $window.location.href = '#/edit/'+ticketId;
    };
    $scope.loadMore = function(){
        if($scope.canPage){
            $scope.canPage = false; //Trying to stop concurrent requests. Happens when scrolling on render
            $http.get('app/ticket?page='+$scope.qPage+'&size='+$scope.qSize).success(function(data){
                console.log('data.length --> '+ data.length)
                if(data.length == 0){
                    //no more results
                    $scope.canPage = false;
                    return;
                }
                data.forEach(function(entry) {
                    $scope.tickets.push(entry);
                });
                if(data.length < $scope.batchSize){
                    $scope.canPage = false;
                    return;
                }
                $scope.qPage = $scope.qPage + $scope.batchSize;
                $scope.canPage = true;
                console.log('$scope.tickets.length --> '+$scope.tickets.length)
            });
        }
    }
}
controllers.View.$inject = ['$scope', '$http', '$log', '$window'];

//EDIT Controller

controllers.Edit = function($scope, $http, $log, $routeParams, $window) {
    $http.get('app/ticket/'+$routeParams.ticketId).success(function(data){
        $scope.data = data;
        if (data.status == 'Closed'){
            $scope.disabled = true
        } else {
            $scope.disabled = false
        }
        $scope.editMode = true;
    });
    $http.get('app/comment/'+$routeParams.ticketId).success(function(data){
        $scope.comments = data;
        $scope.hasComments = true;
    });
    $scope.updateTicket = function(){
        delete $scope.data.comment
        $http.put('app/ticket', $scope.data)
                .success(function(data) {
            $scope.data = data;
                    //$window.location.href = '#/edit/'+data.id;
        });
    };
    $scope.addComment = function(){
        var comment = {
            ticketId : $scope.data.id,
            text : $scope.data.comment
        }
        $http.post('app/comment', comment)
                               .success(function(data) {
            $scope.comments.unshift(data)
            $scope.data.comment = "";
            $window.location.href = '#/edit/'+$scope.data.id;
        });
    };
    $scope.cancel = function(){
        $window.location.href = '#/view';
    }
    $scope.resolve = function(){
        var updateObj = {
            status : "Closed",
            id : $scope.data.id
        }
        $http.put('app/ticket', updateObj)
                .success(function(data) {
                $scope.disabled = true;
                $window.location.href = '#/edit/'+data.id;
        });
    }
}
controllers.Edit.$inject = ['$scope', '$http', '$log', '$routeParams', '$window'];

return controllers;

});