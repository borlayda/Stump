'use strict';

angular.module('Project', ['ngCookies'])
.controller('ProjectController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
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
            },
            function errorCallback(response){
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
            $scope.selProject.status = status;
        }

        // Modal Windows
        $scope.addCreateProjectWindow = function (){
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
            $('#createProject').modal('show');
        }
    }
]);