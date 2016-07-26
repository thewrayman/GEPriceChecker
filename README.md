# GEPriceChecker

Relatively simple Java program to utilise RSBot to check GE prices.

This was purposed to monitor the profitability in tanning hides so it currently hard coded to only look for hides etc.
I may modify it to be more extensible.

The general flow:
Buy 1 of each of the hides at inb price to get high end.
Collect them all to inv.
Sell them at ins price to get low end.

-> repeat for leather.

Wait a period of time before checking again(approx 1hr)
Periodically interact with environment to stay active in between checks.


End goal is to dump these prices to an xml and display it on a web portal on my server where the stats can be viewed.


The tricky part is reliable interactions with GE.. the joys of widgets ensues.
