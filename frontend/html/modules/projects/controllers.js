'use strict';

angular.module('Project', ['ngCookies'])
.controller('ProjectController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $scope.TASK_TYPES = ["ISSUE", "TASK"];
        $scope.PROJECT_STATUSES = ["OPEN", "IN_PROGRESS", "RESOLVED", "REOPENED", "CLOSED"];
        $scope.projectStatusLayout = {
             'btn-success': $scope.selProject.status == 'CLOSED',
             'btn-info': $scope.selProject.status == 'IN_PROGRESS',
             'btn-success': $scope.selProject.status == 'RESOLVED',
             'btn-info': $scope.selProject.status == 'OPEN',
             'btn-warning': $scope.selProject.status == 'REOPENED'
        };

        $http.get('/api/projects').then(function successCallback(response){
            $scope.projects = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Create user
        $scope.createProject = function(title, description) {
            console.log($scope.loginUser);
            console.log($scope.projects);
            $http.post('/api/projects', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "status": 'OPEN'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.projects.push({
                    "title": title,
                    "description": description,
                    "ownerId": $scope.loginUser.id,
                    "status": 'OPEN'
                });
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new project!\n"+response.data);
                console.error(response);
            });
        }

        // Delete user
        $scope.deleteProject = function(project) {
            $http.delete('/api/projects/'+project.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                for (var i=0; i < $scope.projects.length; i++){
                    if ($scope.projects[i].id = user.id){
                        delete $scope.projects[i];
                    }
                }
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Change project status
        $scope.changeProjectStatus = function(status) {
            console.log($scope.selProject.id + " " + status);
            $http.post('/api/projects/change-status', {
                "id": $scope.selProject.id,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.selProject.status = status;
            },
            function errorCallback(response){
                console.error(response);
            });
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
                $scope.tasks.push({
                    "title": title,
                    "description": description,
                    "owner": {"id":$scope.loginUser.id},
                    "status": 'OPEN',
                    "type": type
                });
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new task!\n"+response.data);
                console.error(response);
            });
        }

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $('#createTask').modal('show');
        }
        $scope.addCreateProjectWindow = function (){
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
            $('#createProject').modal('show');
        }
        $scope.addChangeProjectWindow = function (){
            $('#changeProject').modal('show');
        }
    }
]);