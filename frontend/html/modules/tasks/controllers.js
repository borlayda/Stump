'use strict';

angular.module('Task', ['ngCookies'])
.controller('TaskController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $scope.selTask = $cookies.getObject('selTask');
        $scope.tasks = [];
        $http.get('/api/tasks').then(function successCallback(response){
            $scope.tasks = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });
        $scope.loginUser = {}
        $http.get('/api/users/me').then(function successCallback(response){
            $scope.loginUser = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Get all users
        $scope.getTasks = function () {
            $http.get('/api/tasks').then(function successCallback(response){
                $scope.tasks = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Get specific task
        $scope.getTask = function (task) {
            $cookies.put("selTask", JSON.stringify(task));
            $scope.switchActivePart('tasks', 'task');
        }

        // Create user
        $scope.createTask = function(title, description) {
            console.log($scope.loginUser);
            console.log($scope.tasks);
            $http.post('/api/tasks', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "status": 'OPEN'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.tasks.push({
                    "title": title,
                    "description": description,
                    "ownerId": $scope.loginUser.id,
                    "status": 'OPEN'
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Delete user
        $scope.deleteTask = function(task) {
            $http.delete('/api/tasks/'+task.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                for (var i=0; i < $scope.tasks.length; i++){
                    if ($scope.tasks[i].id = user.id){
                        delete $scope.tasks[i];
                    }
                }
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $('#createTask').modal('show');
        }
    }
]);