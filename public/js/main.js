/*global require, requirejs */

'use strict';

requirejs.config({
  urlArgs : "v=0.2",
  paths: {
    'angular': ['../lib/angularjs/angular'],
    'angular-route': ['../lib/angularjs/angular-route'],
    'angular-cookies': ['../lib/angularjs/angular-cookies'],
    'bootstrap' : ['../lib/angular-ui-bootstrap/ui-bootstrap'],
    'ng-infinite-scroll' : ['../lib/ngInfiniteScroll/ng-infinite-scroll']
  },
  shim: {
    'angular': {
      exports : 'angular'
    },
    'angular-route': {
      deps: ['angular'],
      exports : 'angular'
    },
    'angular-cookies': {
        deps: ['angular'],
        exports : 'angular'
    },
    'bootstrap': {
      deps: ['angular'],
      exports : 'angular'
    },
    'ng-infinite-scroll' : {
         deps: ['angular'],
         exports : 'angular'
    }
  }
});

require(['angular', './controllers', './directives', './filters', './services', 'angular-route', 'bootstrap', 'angular-cookies', 'ng-infinite-scroll' ],
  function(angular, controllers) {

    // Declare app level module which depends on filters, and services
    angular.module('myApp', ['ui.bootstrap','myApp.filters', 'myApp.services', 'myApp.directives', 'ngRoute', 'ngCookies', 'infinite-scroll' ]).
      config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'partials/login.html', controller: controllers.Login});
        $routeProvider.when('/logout', {templateUrl: 'partials/login.html', controller: controllers.Logout});
        $routeProvider.when('/register', {templateUrl: 'partials/register.html', controller: controllers.Login});
        $routeProvider.when('/create', {templateUrl: 'partials/create.html', controller: controllers.Create});
        $routeProvider.when('/edit/:ticketId?', {templateUrl: 'partials/create.html', controller: controllers.Edit});
        $routeProvider.when('/view', {templateUrl: 'partials/view.html', controller: controllers.View});
        $routeProvider.otherwise({redirectTo: '/'});
      }]);

      /*angular.module('myApp')
           .run(['$rootScope', '$location', 'authProvider', '$http', function ($rootScope, $location, authProvider, $http) {
              $rootScope.$on('$routeChangeStart', function (event) {
              if (!authProvider.isLoggedIn() && $location.$$path != '/register' && $location.$$path != '/') {
              //Some hack to not to redirect to login on refresh. Cookie based
              var response = null;

                 $http.get('hasLoggedIn').then(
                    function(data){
                        authProvider.setUser(data.data);
                        $location.path('/view');
                    },
                    function(){
                        response = "Not Authorized"
                    }
                 );


                 event.preventDefault();
                 $location.path('/');
              }
              else {
              //ALLOW
              }
        });
      }]);

      angular.module('myApp')
        .factory('authProvider', function() {
          var user;
            return {
              setUser : function(aUser){
                user = aUser;
              },
              isLoggedIn : function(){
                return(user)? user : false;
              }
            };
        });
*/
    angular.bootstrap(document, ['myApp']);

});
