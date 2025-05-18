# --> Symbol is used for comments

# Home Test API
# Run API Locally by below commands

1.docker pull automaticbytes/demo-app
2.docker run -p 3100:3100 automaticbytes/demo-app

# Use below command if above is not working:
# docker run --platform linux/amd64 -p 3100:3100 automaticbytes/demo-app


# API base URL: http://localhost:3100/api

# To Run Tests -> 
1.Open Eclipse

2.Right-click InventoryApiTests.java

3.Select Run As → JUnit Test

#Features Covered
1.Get all inventory items

2.Filter by item ID

3.Add item (new ID)

4.Add item (existing ID)

5.Add item with missing data

6.Validate recently added item is present