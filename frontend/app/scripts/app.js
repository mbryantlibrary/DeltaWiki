'use strict';

/**
 * @ngdoc overview
 * @name deltaWikiApp
 * @description
 * # deltaWikiApp
 *
 * Main module of the application.
 */
angular
  .module('deltaWikiApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/p/:pagename', {
          templateUrl: 'views/page.html',
          controller: 'PageCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

