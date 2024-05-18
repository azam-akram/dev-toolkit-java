'use strict';

angular.module('creditCardApp').factory('CreditCardService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllCreditCards: loadAllCreditCards,
                getAllCreditCard: getAllCreditCard,
                addCreditCard: addCreditCard
            };

            return factory;

            function loadAllCreditCards() {
                console.log('Fetching all credit cards');
                var deferred = $q.defer();
                $http.get(urls.CREDIT_CARD_SERVICE_URL)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all credit cards');
                            $localStorage.cards = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading credit cards');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllCreditCard(){
                return $localStorage.cards;
            }

            function addCreditCard(creditCardInput) {
                var deferred = $q.defer();
                $http.post(urls.CREDIT_CARD_SERVICE_URL, creditCardInput)
                    .then(
                        function (response) {
                            loadAllCreditCards();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.log(errResponse);
                           console.error('Error while adding new credit card : '+ errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }
    ]);