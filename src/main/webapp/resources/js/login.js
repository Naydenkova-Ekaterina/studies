

var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');


class MyForm extends React.Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        data.forEach((value, key) => {data[key] = value});
        var json = JSON.stringify(data);


        console.log(json);
        var logObj = JSON.parse(json);
        var result = "email=" + logObj.email + "&password=" + logObj.password;
        console.log(result);


        $.ajax({
            url: CONTEXT_PATH + '/loginAction',
            type: "POST",
            data: result,
            contentType:' application/x-www-form-urlencoded',
            success: function () {
                console.log("success")
                document.getElementById('logoId').click();
            }
        });

    }

    render() {
        return (


            <div className = "limiter" >
            < div className = "container-login100" >
            < div className = "wrap-login100 p-l-110 p-r-110 p-t-62 p-b-33" >
            < form className = "login100-form validate-form flex-sb flex-w" onSubmit={this.handleSubmit}>
            < span className = "login100-form-title p-b-53" >Sign In</span>

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
    <span className="txt1">
    Password
    </span>

    </div>
    <div className="wrap-input100 validate-input" data-validate = "Password is required">
    <input className="input100" type="password" name="password" type="password" />
    <span className="focus-input100"></span>
    </div>

    <div className="container-login100-form-btn m-t-17">
    <button className="login100-form-btn">
    Sign In
    </button>
    </div>

    <div className="w-full text-center p-t-55">
    <span className="txt2">
    Not a member?
    </span>

    <a href={CONTEXT_PATH+"/reg"} className="txt2 bo1">Sign up now</a>
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