var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');
document.getElementsByClassName("forDrivers").hidden=true;



class MyForm extends React.Component {
    constructor() {
        super();
        this.state = {role: ""};

        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChangeRole = this.onChangeRole.bind(this);

    }

    onChangeRole(e) {

        var val = e.target.value;
        this.setState({role: val});
        if (val == "ROLE_DRIVER") {
            document.getElementById('ifDriver').style.display = 'block';
        } else {
            document.getElementById('ifDriver').style.display = 'none';
        }
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        data.forEach((value, key) => {data[key] = value});
        console.log(data.name);
        var user = {
            email: data.email,
            password:data.password,
            userRole: data.userRole
        };

        $.ajax({
            url: CONTEXT_PATH + '/user/add',
            type: "POST",
            data: JSON.stringify(user),
            contentType:' application/json',
            success: function () {
                console.log("user add success")
                if (data.userRole == "ROLE_DRIVER") {

                    var driver = {
                        name: data.name,
                        surname: data.surname,
                        workedHours: "00:00",
                        status: data.status,
                        city_id: data.city
                    };
                    console.log(driver);

                    fetch(CONTEXT_PATH + '/driver/registerAdd/' + user.email, {
                        method: 'POST',
                        headers: {
                            'Content-Type': ' application/json'
                        },
                        body: JSON.stringify(driver)
                    });

                }
            }
        });

    }

    render() {
        return (



            <div className = "limiter" >
            < div className = "container-login100" >
            < div className = "wrap-login100 p-l-110 p-r-110 p-t-62 p-b-33" >
            < form className = "login100-form validate-form flex-sb flex-w" onSubmit={this.handleSubmit}>
            < span className = "login100-form-title p-b-53" >Sign Up</span>

        {/*<a href="#" className="btn-face m-b-20">*/}
        {/*    <i className="fa fa-facebook-official"></i>Facebook</a>*/}

        {/*<a href = "#" className = "btn-google m-b-20" >*/}
        {/*    <img src = "images/icons/icon-google.png" alt = "GOOGLE" />Google</a>*/}

        <div className="p-t-31 p-b-9">
						<span className="txt1">
							Email
						</span>
        </div>
        < div className = "wrap-input100 validate-input" >
            < input className = "input100" type = "text" id="email" name="email" />
            < span className = "focus-input100" > </span>
    </div>

    <div className="p-t-13 p-b-9">
    <span className="txt1">Password</span>
    </div>

    <div className="wrap-input100 validate-input" data-validate = "Password is required">
    <input className="input100" type="password" name="password" type="password" />
    <span className="focus-input100"></span>
    </div>

                <div className="p-t-13 p-b-9">
                    <span className="txt1">Role</span>
                </div>
                <div className="wrap-input100 validate-input" data-validate = "Password is required">
                    <select className="input100" name="userRole" value={this.state.role} onChange={this.onChangeRole}>
                        <option value="ROLE_EMPLOYEE">Employee</option>
                        <option value="ROLE_DRIVER">Driver</option>
                    </select>
                    <span className="focus-input100"></span>
                </div>



                <div id="ifDriver" style={{display:'none'}}>
                    <div className="p-t-13 p-b-9 forDrivers">
                        <span className="txt1">Name</span>
                    </div>
                    <div className="wrap-input100 validate-input forDrivers" >
                        <input className="input100" name="name" type="text" />
                        <span className="focus-input100"></span>
                    </div>

                    <div className="p-t-13 p-b-9 forDrivers">
                        <span className="txt1">Surname</span>
                    </div>
                    <div className="wrap-input100 validate-input forDrivers" >
                        <input className="input100" name="surname" type="text" />
                        <span className="focus-input100"></span>
                    </div>

                    <div className="p-t-13 p-b-9 forDrivers">
                        <span className="txt1">Status</span>
                    </div>
                    <div className="wrap-input100 validate-input forDrivers" >
                        <select className="input100" name="status" >
                            <option value="rest">Rest</option>
                            <option value="shift">Shift</option>
                            <option value="behindTheWheel">Behind the wheel</option>
                        </select>
                        <span className="focus-input100"></span>
                    </div>


                    <div className="p-t-13 p-b-9 forDrivers">
                        <span className="txt1">City</span>
                    </div>
                    <div className="wrap-input100 validate-input forDrivers" >
                        <select className="input100" id="idCities" name="city" >
                        </select>
                        <span className="focus-input100"></span>
                    </div>


                </div>





    <div className="container-login100-form-btn m-t-17">
    <button className="login100-form-btn">
        CREATE ACCOUNT
    </button>
    </div>


    </form>
    </div>
    </div>
    <div id="dropDownSelect1"></div>

    </div>







        );

    }
}

ReactDOM.render(<MyForm />, document.getElementById('app'));

$.ajax({
    type: "GET",
    url: CONTEXT_PATH + "/cities/listCitiesDTO",
    success: function(data){
        console.log(data);
         $.each(data, function(i, d) {
             $('#idCities').append('<option value="' + d.id + '">' + d.name + '</option>');
         });
    }
});

