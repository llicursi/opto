import {User} from './user';
import * as moment from 'moment';

export class Subject {
  id: number;
  title: string;
  user: User;
  description: string;
  start: string;
  due: string;

  dueFromNow() {
    moment(this.due).fromNow(); // 7 years ago
  }
}
