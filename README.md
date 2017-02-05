# campsite-gap-rule
basic implementation of a campsite gap rule that disallows the creation of schedule gaps of a certain size

test

The App class demonstrates how to properly initialize and use the CampsiteAvailabilityQuery and AvailabilityDisplay classes. The CampsiteAvailabilityQuery class will accept both a org.json.simple.JSONObject as well as a String as constructor arguments. The justification for this was that if these classes were to be used in a real world setting, the JSON data would probably not come from a file already written to disk, and most JSONObjects can be converted to strings on the fly.   

in a production system, dates before today's would be prohibited, but the sample file has dates from last summer.    