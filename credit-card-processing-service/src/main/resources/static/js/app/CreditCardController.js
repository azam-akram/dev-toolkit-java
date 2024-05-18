'use strict';

angular.module('creditCardApp').controller('CreditCardController',
    ['CreditCardService', '$scope',  function( CreditCardService, $scope) {

        var self = this;
        self.creditCardInput = {};
        self.users=[];

        self.submit = submit;
        self.getAllCreditCard = getAllCreditCard;
        self.addCreditCard = addCreditCard;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Saving New credit card');
            validateCardNumberByLuhn10(self.creditCardInput)
            addCreditCard(self.creditCardInput);
        }

        function addCreditCard(creditCardInput) {
            console.log('About to add new credit card');
            CreditCardService.addCreditCard(creditCardInput)
                .then(
                    function (response) {
                        console.log('Credit card added successfully');
                        self.successMessage = 'Credit card added successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.creditCardInput={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while adding new credit card');
                        self.errorMessage = 'Error while adding new credit card: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }

        function getAllCreditCard(){
            return CreditCardService.getAllCreditCard();
        }

        function validateCardNumberByLuhn10(creditCardInput) {
            console.log('Validating card number');
            var cardNumber = creditCardInput.cardNumber;
            var allArray = [];
            var indexAll = 0;
            var sum = 0;
            var checked;

            for (var i = cardNumber.length - 2; i >= 0; i -= 1) {
                allArray[indexAll]
                var num = parseInt(cardNumber.charAt(i), 10);
                if (i % 2 == 1) {
                    num = num*2;
                    if(num > 9) {
                        num = num - 9;
                    }
                }
                allArray[indexAll] = num;
                sum += num;
                indexAll++;
            }

            checked = sum * 9 % 10;
            var checksum = sum + checked;
            if(checksum % 10 == 0) {
                console.log("Valid");
                // Just save this card
            } else {
                console.log("Invalid credit card number");
                self.errorMessage = 'Invalid credit card number: ' + cardNumber;
            }
        }

    }]);