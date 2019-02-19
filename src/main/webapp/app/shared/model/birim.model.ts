export interface IBirim {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    carpan?: number;
}

export class Birim implements IBirim {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public kisaltma?: string, public carpan?: number) {}
}
