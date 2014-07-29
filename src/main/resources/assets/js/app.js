( function() {
    var app = angular.module('scunt', ['directive.g+signin']);
    app.controller("ScuntController", function($rootScope) {
        $rootScope.appSettings = {
            loggedIn: false,
            tab: "home"
        }
        this.setTab = function(newTab) {
            $rootScope.appSettings.tab = newTab;
        };
        this.isTab = function(newTab) {
            return $rootScope.appSettings.tab === newTab;
        };
        this.tabUrl = function() {
            var url = "templates/home/" + $rootScope.appSettings.tab + ".html";
            return url;
        };
        this.isLoggedIn = function() {
            return $rootScope.appSettings.loggedIn;
        };
    });

    app.controller("LoginController", function($rootScope) {
        $rootScope.$on('event:google-plus-signin-success', function (event,authResult) {
            $rootScope.$apply(function() {
                $rootScope.appSettings.loggedIn=true;
            });
            // Callback to successful sign in
            var i = 1;
        });
    });

    app.directive("bodyDirective", function() {
        return {
            restrict: 'E',
            template: '<div ng-include="scuntCtrl.tabUrl()"></div>'
        };
    });

    app.directive("menuNavbar", function() {
        return {
            restrict: 'E',
            templateUrl: "templates/menu/menuTemplate.html"
        };
    });
})();
