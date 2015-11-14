'use strict';

angular.module('Dashboard', ['ngCookies'])
.controller('DashboardController',
    ['$scope', '$rootScope', '$location', '$cookies', '$window', '$http',
    function ($scope, $rootScope, $location, $cookies, $window, $http) {
        $scope.dashboardPartActive = 'projects';
        $scope.user = JSON.parse($cookies.get("user"));

        $scope.switchActivePart = function($scope, partName) {
            console.log("Dashboard changed: Part = " + partName);

            $scope.dashboardPartActive = patName;
        }
        $scope.logout = function () {
             console.log($scope.user.name);
             console.log("Logging out ...");
             $window.location.assign("/#/login");
        }
    }
]);