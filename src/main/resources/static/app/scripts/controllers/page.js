'use strict';

/**
 * @ngdoc function
 * @name deltaWikiApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the deltaWikiApp
 */
angular.module('deltaWikiApp')
  .controller('PageCtrl', function ($scope,$http,$routeParams) {

      $scope.pageNotFound = false;
      $scope.editing = false;
      $scope.pageName = $routeParams.pagename;
      document.title = $scope.pageName;

      $scope.enableEdit = function(editingEnabled) {
          $scope.editedContent = $scope.pageContent;
          $scope.editing = editingEnabled;
      };

      // get page from server
      $http.get('/api/page/' + encodeURIComponent($scope.pageName)).then(
          function(response) { //200, success
              $scope.pageContent = response.data.pageContent;
              $scope.editedContent = $scope.pageContent;
          },
          function(response) { //error
              if(response.status === 404) {
                  $scope.pageContent = "";
                  $scope.editedContent = "";
                  $scope.pageNotFound = true;
              }
          }
      );

      $scope.savePage = function() {
          $http.put(
              '/api/page/' + encodeURIComponent($scope.pageName),
              {
                  pageName: $scope.pageName,
                  pageContent: $scope.editedContent
              }).then(
                  function(response) {
                      $scope.pageContent = response.data.pageContent;
                      $scope.editedContent = $scope.pageContent;
                      $scope.editing = false;
                  },
                  function(response) {
                      console.log('Error');
                  }
              )};
  });
