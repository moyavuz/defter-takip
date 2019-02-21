import { IHakedis } from 'app/shared/model/hakedis.model';
import { IPoz } from 'app/shared/model/poz.model';

export interface IHakedisDetay {
    id?: number;
    miktar?: number;
    hakedis?: IHakedis;
    poz?: IPoz;
}

export class HakedisDetay implements IHakedisDetay {
    constructor(public id?: number, public miktar?: number, public hakedis?: IHakedis, public poz?: IPoz) {}
}
