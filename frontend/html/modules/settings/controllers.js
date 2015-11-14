'use strict';

angular.module('Settings', ['ngCookies'])
.controller('SettingsController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {

        // Modal Windows
        $scope.addChangePasswordWindow = function (){
            $('#changePassword').modal('show');
        }
        $scope.addChangeRoleWindow = function (){
            $('#changeRole').modal('show');
        }
    }
]);