'use strict';

angular.module('Task', ['ngCookies'])
.controller('TaskController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $http.get('/api/tasks').then(function successCallback(response){
            $scope.tasks = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Create task
        $scope.createTask = function(title, description, projectId) {
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
                    "owner": {"id":$scope.loginUser.id},
                    "status": 'OPEN'
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Create comment
        $scope.addComment = function(taskId, text) {
            $http.post('/api/comments', {
                "taskId": taskId,
                "text": text
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.selTask.comments.push({
                    "author": {"name": $scope.loginUser.name},
                    "timestamp": Date.now(),
                    "text": text
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Convert timestamp to date
        $scope.convertToDate = function(timestamp) {
            console.log(timestamp);
            var date = new Date(timestamp);
            var hours = date.getHours();
            var minutes = "0" + date.getMinutes();
            var seconds = "0" + date.getSeconds();

            var years = date.getFullYear();
            var months = date.getMonth();
            var days = date.getDate();

            // Will display time in 10:30:23 format
            var formattedTime = years + '.' + months + '.' + days + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
            return formattedTime;
        }

        // Change Task's status
        $scope.changeTaskStatus = function(status) {
            $scope.selTask.status = status;
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