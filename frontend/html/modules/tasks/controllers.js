'use strict';

angular.module('Task', ['ngCookies'])
.controller('TaskController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $scope.TASK_TYPES = ["ISSUE", "TASK"];
        $scope.TASK_STATUSES = ["OPEN", "IN_PROGRESS", "REVIEW", "TESTING", "VERIFYING", "CLOSED"]
        $scope.selectUser = []

        $http.get('/api/tasks').then(function successCallback(response){
            $scope.tasks = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });
        $scope.showAddComment = false;

        $scope.showCommentDiv = function(){
            $scope.showAddComment = true;
        }

        $scope.unshowCommentDiv = function(){
            $scope.showAddComment = false;
        }

        $scope.showAddSubComment = false;

        $scope.showSubCommentDiv = function(){
            $scope.showAddSubComment = true;
        }

        $scope.unshowSubCommentDiv = function(){
            $scope.showAddSubComment = false;
        }

        // Create task
        $scope.createTask = function(title, description, projectId, type) {
            console.log($scope.loginUser);
            console.log($scope.tasks);
            $http.post('/api/tasks', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "projectId": projectId,
                "status": 'OPEN',
                "type": type
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTasks();
                $scope.title = "";
                $scope.description = "";
                $scope.type = "";
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
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                alert("Can't add new task!\n"+response.data);
                console.error(response);
            });
        }

        // Create subcomment
        $scope.addSubComment = function(comment, text) {
            $http.post('/api/comments', {
                "commentId": commentId,
                "text": text
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                alert("Can't add new task!\n"+response.data);
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

        // Change Task's data
        $scope.changeTask = function(taskId, title, description, ownerId, status) {
            $http.post('/api/tasks/change', {
                "taskId": taskId,
                "title": title,
                "description": description,
                "ownerId": ownerId,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Change Task's status
        $scope.changeTaskStatus = function(status) {
            $http.post('/api/tasks/change-status', {
                "id": $scope.selTask.id,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Add task worktime
        $scope.addWorkTime = function(workTime) {
            console.log(workTime);
            console.log($scope.selTask.workTime);
            $http.post('/api/tasks/change-worktime', {
                "id": $scope.selTask.id,
                "workTime": workTime
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Delete task
        $scope.deleteTask = function(task) {
            $http.delete('/api/tasks/'+task.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTasks();
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Delete comment
        $scope.deleteComment = function(comment) {
            $http.delete('/api/comments/'+comment.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getTask($scope.selTask);
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $('#createTask').modal('show');
        }
        $scope.addChangeTaskWindow = function (){
            $('#changeTask').modal('show');
        }
    }
]);