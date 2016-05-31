import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UatSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://kitchensink-uat.apps.ose.ulx.hu")
		.inferHtmlResources()



    val uri1 = "http://kitchensink-uat.apps.ose.ulx.hu"

	val scn = scenario("IntTestSimulation")
		.exec(http("request_0")
			.get("/"))
		.pause(1)
		.exec(http("request_1")
			.get("/index.jsf")
                        .check(substring("uat-fail").notExists))
		.pause(20)
		.exec(http("request_2")
			.post("/index.jsf")
			.formParam("reg", "reg")
			.formParam("reg:name", "Jane Doe")
			.formParam("reg:email", "jdoe@user.com")
			.formParam("reg:phoneNumber", "12345678901")
			.formParam("reg:register", "Register"))
		.pause(20)
		.exec(http("request_3")
			.get("/rest/members"))
		.pause(20)
		.exec(http("request_4")
			.get("/index.jsf"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol).assertions(
          global.failedRequests.count.is(0)
        )
}


