'use strict';

// declare modules

angular.module('DashboardChanger', [
    'User',
    'Settings',
    'Project',
    'Task',
    'ngRoute'
])

.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/users', {
            controller: 'UserController',
            templateUrl: 'modules/users/views/users.html'
        })

        .when('/tasks', {
            controller: 'TaskController',
            templateUrl: 'modules/tasks/views/tasks.html'
        })

        .when('/projects', {
            controller: 'ProjectController',
            templateUrl: 'modules/projects/views/projects.html'
        })

        .when('/user/settings', {
            controller: 'SettingsController',
            templateUrl: 'modules/settings/views/settings.html'
        })

        .otherwise({ redirectTo: '/projects' });
}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('JSESSIONID') || {};
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals) {
                $location.path('/login');
            }
        });
    }]);