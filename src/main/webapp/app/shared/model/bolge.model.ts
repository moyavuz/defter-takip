import { IProje } from 'app/shared/model/proje.model';
import { ICalisan } from 'app/shared/model/calisan.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    bolges?: IProje[];
    sorumluCalisan?: ICalisan;
}

export class Bolge implements IBolge {
    constructor(
        public id?: number,
        public ad?: string,
        public adres?: string,
        public bolges?: IProje[],
        public sorumluCalisan?: ICalisan
    ) {}
}
