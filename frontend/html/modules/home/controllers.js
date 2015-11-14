'use strict';

angular.module('Dashboard', ['ngCookies'])
.controller('DashboardController',
    ['$scope', '$rootScope', '$location', '$cookies', '$window', '$http',
    function ($scope, $rootScope, $location, $cookies, $window, $http) {
        $scope.dashboardHTMLActive = 'projects';
        $scope.dashboardPartActive = 'projects';

        $scope.switchActivePart = function(part, html) {
            $scope.dashboardPartActive = part;
            $scope.dashboardHTMLActive = html;
        }
        $scope.logout = function () {
             console.log("Logging out ...");
             $window.location.assign("/#/login");
        }
    }
]);