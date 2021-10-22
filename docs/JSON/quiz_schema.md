Type: `Quiz`

**_Properties_**

 - <b id="#/properties/name">name</b> `required`
	 - Type: `string`
 - <b id="#/properties/questions">questions</b> `required`
	 - Type: `array`
		 - **_Items_**
		 - Type: `Question`
		 - **_Properties_**
			 - <b id="#/properties/questions/items/properties/question">question</b> `required`
				 - Type: `string`
			 - <b id="#/properties/questions/items/properties/answer">answer</b> `required`
				 - Type: `integer`
			 - <b id="#/properties/questions/items/properties/choices">choices</b> `required`
				 - Type: `array`
					 - **_Items_**
					 - Type: `string`
					 - Type: `string`
					 - Type: `string`
					 - Type: `string`

