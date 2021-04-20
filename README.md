Remote repository for ParkingGarage.java progect CSCI 2101

  I've made a branch for us each to do our own work on,
  so that we can work on the code and make saves without
  interfering with each other. Before pushing an update
  to the main branch, please talk to the group.


SOME USEFUL GIT COMMANDS & DESCRIPTIONS:
-git add . (adds all untracked files to the staging area)

-git add filename (adds one specific untracked file to the staging area)

-git commit -m reason (commits the files in the staging area to the repository,
with "reason" being the description of the update/commit)

-git status (tells you the status of all files IE if theyre in staging area or not)

-git rm --cached name (removes an added file for a commit)

-git checkout -b "name" (creates a new branch and moves to it)

-git push origin branch-name (PUSHES any new commits to the remote repository)

SETTING UP YOUR FILE WITH THE REMOTE REPOSITORY:
git branch -M main
git remote add origin https://github.com/miiiiip/ParkingGarage.git
git pull origin main