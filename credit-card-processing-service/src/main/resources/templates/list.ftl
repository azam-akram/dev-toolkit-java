<div class="col-md-6">
    <div class="generic-container">
		<div class="panel panel-default">
			<div class="panel-heading"><span class="lead">Credit Card System</span></div>
			<div class="panel-body">
				<h4><strong>Add</strong></h4>
				<br>
				<div class="formcontainer">
					<div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
					<div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
					<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
						<input type="hidden" ng-model="ctrl.creditCardInput.cardNumber" />
						<div class="row">
							<div class="form-group col-md-12">
								<label class="col-md-4 control-lable" for="cname">Name</label>
								<div class="col-md-8">
									<input type="text" name="cname" ng-model="ctrl.creditCardInput.name" id="cname" class="username form-control input-sm"
										   placeholder="Enter customer name" required ng-minlength="3"/>
                                    <span>The name is required.</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label class="col-md-4 control-lable" for="cardNumber">Card Number</label>
								<div class="col-md-8">
									<input type="text" name="cardNumber" ng-model="ctrl.creditCardInput.cardNumber" id="cardNumber"
                                           minlength='13' maxlength='19' data-ng-pattern='ctrl.onlyNumbers'
										    class="form-control input-sm" placeholder="Enter credit card number." required/>
                                    <span>Card number is required, Only 13 to 19 digits are allowed.</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<label class="col-md-4 control-lable" for="salary">Limit</label>
								<div class="col-md-8">
									<input type="text" name="accountLimit" ng-model="ctrl.creditCardInput.accountLimit" id="accountLimit"
										   class="form-control input-sm" placeholder="Enter account limit."
										   required ng-pattern="ctrl.onlyNumbers"/>
                                    <span>Account limit is required, Only digits are allowed.</span>
								</div>
							</div>
						</div>
						<div class="row">
                            <div class="form-group col-md-12">
                                <div class="form-group col-md-4">
                                </div>
                                <div class="col-md-8">
									<div class="form-actions pull-right">
										<input type="submit"  value="Add" class="btn btn-primary btn-md" ng-disabled="myForm.$invalid || myForm.$pristine">
									</div>
                                </div>
                            </div>
						</div>
					</form>
				</div>
			</div>
		</div>
    </div>
</div>
<div class="col-md-12">
	<div class="generic-container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading"><span class="lead">Existing Cards </span></div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
						<tr>
							<th>Name</th>
							<th>Card Number</th>
							<th>Balance</th>
							<th Limit></th>
						</tr>
						</thead>
						<tbody>
						<tr ng-repeat="u in ctrl.getAllCreditCard()">
							<td>{{u.name}}</td>
							<td>{{u.cardNumber}}</td>
							<td>£{{u.balance}}</td>
							<td>£{{u.limit}}</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>