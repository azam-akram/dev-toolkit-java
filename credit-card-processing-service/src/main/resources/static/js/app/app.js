var app = angular.module('creditCardApp',['ui.router','ngStorage']);

app.constant('urls', {
    CREDIT_CARD_SERVICE_URL : '/credit-card-processing-service/v1/card' });

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'CreditCardController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, CreditCardService) {
                        console.log('Load all cards');
                        var deferred = $q.defer();
                        CreditCardService.loadAllCreditCards().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

