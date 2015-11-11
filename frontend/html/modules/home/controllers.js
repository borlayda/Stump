'use strict';

angular.module('Dashboard')
.controller('DashboardController',
    ['$scope', '$rootScope', '$location',
    function ($scope, $rootScope, $location) {
        $scope.dashboardPartActive = 'projects';

        $scope.switchActivePart = function($scope, partName) {
            $scope.dashboardPartActive = patName;
            console.log("Dashboard changed: Part = " + partName);
        }
    }
]);