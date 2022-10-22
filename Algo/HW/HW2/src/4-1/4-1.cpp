#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;
typedef pair<int,int> pii;
vector<pii> ori;
vector<int> end_time;

bool my_comp(pii a, pii b){
    return a.first < b.first;
}

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n; cin >> n; pii tmp;
    for(int i = 0; i < n; i++){
        cin >> tmp.first >> tmp.second;
        ori.push_back(tmp);
    }
    sort(ori.begin(),ori.end(), my_comp);
    for(int i = 0;i<n;i++){
        bool flag = false;
        tmp = ori[i];
        for(int j = 0; j < end_time.size();j++){
            if(end_time[j] < tmp.first){ 
                end_time[j] = tmp.second;
                flag = true;
                break;
            }
        }
        if(!flag) end_time.push_back(tmp.second);
    }
    cout << end_time.size();
    return 0;
}


